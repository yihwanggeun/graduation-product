package edu.kaist.cs.teamfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.res.stringResource
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.compose.ui.channels.ChannelsScreen
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.offline.model.message.attachments.UploadAttachmentsNetworkType
import io.getstream.chat.android.offline.plugin.configuration.Config
import io.getstream.chat.android.offline.plugin.factory.StreamOfflinePluginFactory

class ChatActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1 - Set up the OfflinePlugin for offline storage
        val offlinePluginFactory = StreamOfflinePluginFactory(
            config = Config(
                backgroundSyncEnabled = true,
                userPresence = true,
                persistenceEnabled = true,
                uploadAttachmentsNetworkType = UploadAttachmentsNetworkType.NOT_ROAMING,
            ),
            appContext = applicationContext,
        )

        // 2 - Set up the client for API calls and with the plugin for offline storage
        val client = ChatClient.Builder(getString(R.string.stream_api_key), applicationContext)
            .withPlugin(offlinePluginFactory)
            .logLevel(ChatLogLevel.ALL) // Set to NOTHING in prod
            .build()

        // 3 - Authenticate and connect the user
        val user = User(
            id = "yihwanggeun",
            name = "yihwanggeun",
            image = "https://bit.ly/2TIt8NR"
        )

        client.connectUser(
            user = user,
            token = client.devToken(user.id)
//            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoidHV0b3JpYWwtZHJvaWQifQ.WwfBzU1GZr0brt_fXnqKdKhz3oj0rbDUm2DqJO_SS5U"
        ).enqueue()

        // 4 - Set up the Channels Screen UI
        setContent {
            ChatTheme {
                ChannelsScreen(
                    title = stringResource(id = R.string.app_name),
                    isShowingSearch = true,
                    onItemClick = { channel ->
                        startActivity(MessagesActivity.getIntent(this, channel.cid))
                    },
                    onBackPressed = { finish() }
                )
            }
        }
    }
}