import streamlit as st
import mysql.connector
import pandas as pd
from datetime import datetime
import plotly.express as px

# DB 연결 설정
@st.cache_resource
def init_connection():
    return mysql.connector.connect(
        host="localhost",
        user="root",
        password="Cowcow24@@",
        database="teamfinder"
    )

# 데이터 쿼리 함수들
def get_projects():
    conn = init_connection()
    query = """
        SELECT 
            p.p_id,
            p.p_name,
            p.p_type,
            p.fullname as creator,
            p.current,
            p.max,
            COUNT(pa.application_id) as total_applications,
            SUM(CASE WHEN pa.status = 'pending' THEN 1 ELSE 0 END) as pending_applications
        FROM projects p
        LEFT JOIN project_applications pa ON p.p_id = pa.project_id
        GROUP BY p.p_id
    """
    return pd.read_sql(query, conn)

def get_project_applications(project_id):
    conn = init_connection()
    query = """
        SELECT 
            pa.application_id,
            pa.applicant_email,
            u.fullname,
            pa.apply_date,
            pa.status,
            pa.message
        FROM project_applications pa
        JOIN users u ON pa.applicant_email = u.email
        WHERE pa.project_id = %s
        ORDER BY pa.apply_date DESC
    """
    return pd.read_sql(query, conn, params=[project_id])

def get_application_stats():
    conn = init_connection()
    query = """
        SELECT 
            DATE(apply_date) as date,
            COUNT(*) as applications,
            SUM(CASE WHEN status = 'accepted' THEN 1 ELSE 0 END) as accepted,
            SUM(CASE WHEN status = 'rejected' THEN 1 ELSE 0 END) as rejected
        FROM project_applications
        GROUP BY DATE(apply_date)
        ORDER BY date DESC
        LIMIT 30
    """
    return pd.read_sql(query, conn)

# Streamlit 앱 메인
def main():
    st.set_page_config(page_title="Project Management Dashboard", layout="wide")
    st.title("프로젝트 관리 대시보드")

    # 사이드바 메뉴
    menu = st.sidebar.selectbox(
        "메뉴 선택",
        ["대시보드", "프로젝트 목록", "지원자 관리"]
    )

    if menu == "대시보드":
        show_dashboard()
    elif menu == "프로젝트 목록":
        show_projects()
    else:
        show_applications()

def show_dashboard():
    # 통계 개요
    col1, col2, col3 = st.columns(3)
    
    projects_df = get_projects()
    stats_df = get_application_stats()
    
    with col1:
        st.metric("총 프로젝트 수", len(projects_df))
    
    with col2:
        total_applications = projects_df['total_applications'].sum()
        st.metric("총 지원자 수", total_applications)
    
    with col3:
        pending_applications = projects_df['pending_applications'].sum()
        st.metric("대기 중인 지원", pending_applications)

    # 지원 현황 그래프
    st.subheader("일별 지원 현황")
    fig = px.line(stats_df, x='date', y=['applications', 'accepted', 'rejected'],
                  title="지원 추이")
    st.plotly_chart(fig)

    # 프로젝트 타입별 분포
    st.subheader("프로젝트 타입별 분포")
    type_dist = projects_df['p_type'].value_counts()
    fig = px.pie(values=type_dist.values, names=type_dist.index,
                 title="프로젝트 타입 분포")
    st.plotly_chart(fig)

def show_projects():
    st.subheader("프로젝트 목록")
    
    projects_df = get_projects()
    
    # 필터링 옵션
    col1, col2 = st.columns(2)
    with col1:
        selected_type = st.multiselect(
            "프로젝트 타입",
            options=projects_df['p_type'].unique()
        )
    
    with col2:
        show_full = st.checkbox("모집 완료된 프로젝트 표시")

    # 필터 적용
    filtered_df = projects_df
    if selected_type:
        filtered_df = filtered_df[filtered_df['p_type'].isin(selected_type)]
    if not show_full:
        filtered_df = filtered_df[filtered_df['current'] < filtered_df['max']]

    # 프로젝트 목록 표시
    st.dataframe(
        filtered_df[[
            'p_id', 'p_name', 'p_type', 'creator', 
            'current', 'max', 'total_applications', 'pending_applications'
        ]],
        hide_index=True
    )

def show_applications():
    st.subheader("지원자 관리")
    
    # 프로젝트 선택
    projects_df = get_projects()
    selected_project = st.selectbox(
        "프로젝트 선택",
        options=projects_df['p_id'].tolist(),
        format_func=lambda x: f"{x}: {projects_df[projects_df['p_id']==x]['p_name'].iloc[0]}"
    )

    if selected_project:
        applications_df = get_project_applications(selected_project)
        
        # 상태별 필터링
        status_filter = st.multiselect(
            "상태 필터",
            options=['pending', 'accepted', 'rejected'],
            default=['pending']
        )
        
        filtered_applications = applications_df[
            applications_df['status'].isin(status_filter)
        ]

        # 지원자 목록 표시
        st.dataframe(
            filtered_applications[[
                'application_id', 'fullname', 'applicant_email',
                'apply_date', 'status', 'message'
            ]],
            hide_index=True
        )

        # 선택한 지원자 상태 업데이트
        if st.button("선택한 지원 수락"):
            # 여기에 상태 업데이트 로직 추가
            st.success("지원이 수락되었습니다.")

if __name__ == "__main__":
    main()