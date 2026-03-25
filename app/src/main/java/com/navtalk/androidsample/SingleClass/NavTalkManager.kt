import android.content.Context
import android.content.Intent
import com.navtalk.androidsample.ChatActivity
import com.navtalk.androidsample.SingleClass.WebsocketManager.appContext
import es.dmoral.toasty.Toasty
import org.webrtc.SurfaceViewRenderer

//定义接口
interface FunctionCallListener {
    fun onFunctionCalled(message: String)
}

object NavTalkManager{
    //1.参数：
    //1.1.licens(必要参数)
    var license: String = "sk_navtalk_tcDB9SaqHKKe7pXc5tyt0Z7aNB0SgI3R"
    //1.2.characterName(必要参数)
    var characterName: String = "Freya"
    //Avatar Image URL
    var avatar_image_url: String = ""
    //Avatar Provide Type Name
    var avatar_provider_type: String = ""
    //是否保留上一次的通话记录
    var isOrNotSaveHistoryChatMessages: Boolean = true
    //Function Call
    var functionsJsonString: String = ""

    //2.函数：
    // 外部注册回调
    var functionCallListener: FunctionCallListener? = null

    public fun showChatActivity(context: Context){
       if (license.count() <= 0){
           Toasty.error(context, "Please enter the required license parameters first.", Toasty.LENGTH_SHORT,true).show()
           return
       }
        if (characterName.count() <= 0){
            Toasty.error(context, "Please enter the required characterName parameters first.", Toasty.LENGTH_SHORT,true).show()
            return
        }
        val intent = Intent(context, ChatActivity::class.java)
        context.startActivity(intent)
    }

}