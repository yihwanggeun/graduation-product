package edu.kaist.cs.teamfinder

import android.content.Context
import androidx.startup.Initializer
import io.getstream.chat.android.client.BuildConfig
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.offline.model.message.attachments.UploadAttachmentsNetworkType
import io.getstream.chat.android.offline.plugin.configuration.Config
import io.getstream.chat.android.offline.plugin.factory.StreamOfflinePluginFactory
import io.getstream.log.streamLog

class StreamChatInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        streamLog { "StreamChatInitializer is initialized"}

        val logLevel = if (BuildConfig.DEBUG) ChatLogLevel.ALL else ChatLogLevel.NOTHING
        val offlinePluginFactory = StreamOfflinePluginFactory(
            config = Config(
                backgroundSyncEnabled = true,
                userPresence = true,
                persistenceEnabled = true,
                uploadAttachmentsNetworkType = UploadAttachmentsNetworkType.NOT_ROAMING
            ),
            appContext = context
        )
        val chatClient = ChatClient.Builder(context.getString(R.string.stream_api_key), context)
            .withPlugin(offlinePluginFactory)
            .logLevel(logLevel)
            .build()

        val user = User(
            id = "hwanggeun",
            name = "hwanggeun",
            image = "https://pacekitten.com/200/300"
        )

        val token = chatClient.devToken(user.id)
        chatClient.connectUser(user, token).enqueue()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> =
        listOf(StreamLogInitializer::class.java)
}