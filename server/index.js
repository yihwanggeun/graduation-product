const express = require('express');
const mysql = require('mysql2');
const bcrypt = require('bcrypt');
const bodyParser = require('body-parser');
const cors = require('cors');
require('dotenv').config();


const app = express();
app.use(bodyParser.json());
app.use(cors());
// Database connection
const connection = mysql.createConnection({
    host: process.env.DB_HOST,
    user: process.env.DB_USER,
    password: process.env.DB_PASSWORD,
    database: process.env.DB_DATABASE
});

connection.connect((err) => {
    if (err) {
        console.error('Error connecting to database:', err);
        return;
    }
    console.log('Connected to MySQL database');
});

// Logging middleware
const logRequest = (req, res, next) => {
    const timestamp = new Date().toISOString();
    console.log(`[${timestamp}] ${req.method} ${req.url}`);
    console.log('Request Body:', req.body);
    console.log('Request Query:', req.query);

    // Capture the original res.json to add response logging
    const originalJson = res.json;
    res.json = function(data) {
        console.log(`[${timestamp}] Response:`, data);
        return originalJson.call(this, data);
    };

    next();
};

// Apply logging middleware to all routes
app.use(logRequest);

// Root endpoint
app.get('/', (req, res) => {
    res.send('Hello World!');
});

app.post('/createUser', async (req, res) => {
    console.log('Processing user creation request...');
    const { email, password, fullname, photo_url } = req.body;

    try {
        const hashedPassword = await bcrypt.hash(password, 10);
        
        // 첫 번째 쿼리: INSERT IGNORE 실행
        const insertQuery = 'INSERT IGNORE INTO users (email, password, fullname, photo_url) VALUES (?, ?, ?, ?)';
        
        connection.query(insertQuery, [email, hashedPassword, fullname, photo_url], (err, insertResult) => {
            if (err) {
                console.error('Database error during insert:', err);
                res.status(500).json({ error: err.message });
                return;
            }

            // 두 번째 쿼리: SELECT로 사용자 정보 조회
            const selectQuery = 'SELECT email, fullname, photo_url FROM users WHERE email = ?';
            
            connection.query(selectQuery, [email], (err, selectResult) => {
                if (err) {
                    console.error('Database error during select:', err);
                    res.status(500).json({ error: err.message });
                    return;
                }

                const isNewUser = insertResult.affectedRows > 0;
                const user = selectResult[0];

                res.status(isNewUser ? 201 : 200).json({
                    message: isNewUser ? 'User created successfully' : 'User already exists',
                    user: {
                        email: user.email,
                        fullname: user.fullname,
                        photo_url: user.photo_url
                    }
                });
            });
        });
    } catch (err) {
        console.error('Error hashing password:', err);
        res.status(500).json({ error: 'Error creating user' });
    }
});

// Create project
app.post('/createProject', (req, res) => {
    console.log('Creating new project...');
    const { p_name, p_description, p_require, p_type, fullname, front, back, max, current } = req.body;
    
    const query = 'INSERT INTO projects (p_name, p_description, p_require, p_type, fullname, front, back, max, current) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)';
    connection.query(query, [p_name, p_description, p_require, p_type, fullname, front, back, max, current], (err, results) => {
        if (err) {
            console.error('Error creating project:', err);
            res.status(500).json({ error: err.message });
            return;
        }
        console.log('Project created successfully:', { p_name, p_type });
        res.json({ ...req.body, p_id: results.insertId });
    });
});

// Check user
app.get('/checkUser', async (req, res) => {
    console.log('Checking user credentials...');
    const { email, password } = req.query;
    
    const query = 'SELECT * FROM users WHERE email = ?';
    connection.query(query, [email], async (err, results) => {
        if (err) {
            console.error('Database error during user check:', err);
            res.status(500).json({ error: err.message });
            return;
        }
        
        if (results.length === 0) {
            console.log('User not found:', email);
            res.status(401).json({ error: 'User not found' });
            return;
        }

        const match = await bcrypt.compare(password, results[0].password);
        if (!match) {
            console.log('Invalid password attempt for user:', email);
            res.status(401).json({ error: 'Invalid password' });
            return;
        }

        console.log('User logged in successfully:', email);
    
        res.json({results});
        
    });
});

// 프로젝트 지원 API
app.post('/applyProject', (req, res) => {
    const { project_id, applicant_email, message } = req.body;
    
    // 먼저 이미 지원했는지 확인
    const checkQuery = `
        SELECT * FROM project_applications 
        WHERE project_id = ? AND applicant_email = ?
    `;
    
    connection.query(checkQuery, [project_id, applicant_email], (err, results) => {
        if (err) {
            console.error('Error checking application:', err);
            res.status(500).json({ error: err.message });
            return;
        }
        
        if (results.length > 0) {
            res.status(400).json({ error: '이미 지원한 프로젝트입니다.' });
            return;
        }
        
        // 새로운 지원 등록
        const applyQuery = `
            INSERT INTO project_applications 
            (project_id, applicant_email, message) 
            VALUES (?, ?, ?)
        `;
        
        connection.query(applyQuery, [project_id, applicant_email, message], (err, results) => {
            if (err) {
                console.error('Error applying to project:', err);
                res.status(500).json({ error: err.message });
                return;
            }
            
            res.json({ 
                message: '프로젝트 지원이 완료되었습니다.',
                application_id: results.insertId 
            });
        });
    });
});

// 프로젝트별 지원자 조회 API
app.get('/projectApplicants/:projectId', (req, res) => {
    const projectId = req.params.projectId;
    
    const query = `
        SELECT 
            pa.application_id,
            pa.applicant_email,
            pa.apply_date,
            pa.status,
            pa.message,
            u.fullname,
            u.photo_url
        FROM project_applications pa
        JOIN users u ON pa.applicant_email = u.email
        WHERE pa.project_id = ?
        ORDER BY pa.apply_date DESC
    `;
    
    connection.query(query, [projectId], (err, results) => {
        if (err) {
            console.error('Error fetching applicants:', err);
            res.status(500).json({ error: err.message });
            return;
        }
        
        res.json(results);
    });
});

// 지원 상태 업데이트 API (수락/거절)
app.put('/updateApplicationStatus', (req, res) => {
    const { application_id, status } = req.body;
    
    const query = `
        UPDATE project_applications 
        SET status = ? 
        WHERE application_id = ?
    `;
    
    connection.query(query, [status, application_id], (err, results) => {
        if (err) {
            console.error('Error updating application status:', err);
            res.status(500).json({ error: err.message });
            return;
        }
        
        // 지원이 수락된 경우 프로젝트의 current 값을 증가
        if (status === 'accepted') {
            const updateProjectQuery = `
                UPDATE projects p
                JOIN project_applications pa ON p.p_id = pa.project_id
                SET p.current = p.current + 1
                WHERE pa.application_id = ?
            `;
            
            connection.query(updateProjectQuery, [application_id], (err) => {
                if (err) {
                    console.error('Error updating project current count:', err);
                    res.status(500).json({ error: err.message });
                    return;
                }
                
                res.json({ 
                    message: '지원 상태가 업데이트되었습니다.',
                    status: status 
                });
            });
        } else {
            res.json({ 
                message: '지원 상태가 업데이트되었습니다.',
                status: status 
            });
        }
    });
});

// Get projects by type
app.get('/typeproject', (req, res) => {
    console.log('Fetching projects by type...');
    const { frame } = req.query;
    
    const query = 'SELECT * FROM projects WHERE front = ? OR back = ?';
    connection.query(query, [frame, frame], (err, results) => {
        if (err) {
            console.error('Error fetching projects by type:', err);
            res.status(500).json({ error: err.message });
            return;
        }
        console.log(`Found ${results.length} projects of type:`, frame);
        res.json(results);
    });
});

// Get project details
app.get('/detailproject', (req, res) => {
    console.log('Fetching project details...');
    const { projectName } = req.query;
    
    const query = 'SELECT * FROM projects WHERE p_name = ?';
    connection.query(query, [projectName], (err, results) => {
        if (err) {
            console.error('Error fetching project details:', err);
            res.status(500).json({ error: err.message });
            return;
        }
        console.log('Project details retrieved:', projectName);
        res.json(results);
    });
});

// Get all projects
app.get('/allproject', (req, res) => {
    console.log('Fetching all projects...');
    const query = 'SELECT * FROM projects';
    connection.query(query, (err, results) => {
        if (err) {
            console.error('Error fetching all projects:', err);
            res.status(500).json({ error: err.message });
            return;
        }
        console.log(`Retrieved ${results.length} projects`);
        res.json(results);
    });
});

// Get all feeds
app.get('/allfeed', (req, res) => {
    console.log('Fetching all feeds...');
    const query = 'SELECT * FROM feeds ORDER BY date DESC';
    connection.query(query, (err, results) => {
        if (err) {
            console.error('Error fetching feeds:', err);
            res.status(500).json({ error: err.message });
            return;
        }
        console.log(`Retrieved ${results.length} feeds`);
        res.json(results);
    });
});

app.post('/createfeed', (req, res) => {
    console.log('Creating new feed...');
    const { title, content, author, name } = req.body;
    
    const query = `
        INSERT INTO feeds (posting_title, posting_content, author, date, posting_like, posting_comment, posting_shared) 
        VALUES (?, ?, ?,  NOW(), 0, 0, 0)
    `;
    
    connection.query(query, [title, content, name], (err, results) => {
        if (err) {
            console.error('Error creating feed:', err);
            res.status(500).json({ error: err.message });
            return;
        }
        console.log('Feed created successfully');
        res.status(201).json({ 
            message: 'Feed created successfully',
            feed_id: results.insertId 
        });
    });
});

// Get feed details
app.get('/detailfeed', (req, res) => {
    console.log('Fetching feed details...');
    const { post_id } = req.query;
    
    const query = 'SELECT * FROM feeds WHERE posting_id = ?';
    connection.query(query, [post_id], (err, results) => {
        if (err) {
            console.error('Error fetching feed details:', err);
            res.status(500).json({ error: err.message });
            return;
        }
        console.log('Feed details retrieved for post:', post_id);
        res.json(results);
    });
});

const PORT = process.env.PORT || 8080;
app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});