import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import com.google.gson.Gson
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.SendMessageRequest
import software.amazon.awssdk.services.sqs.model.SqsException
import java.io.*
import java.nio.charset.Charset
class Sqs : RequestStreamHandler {

    override  fun handleRequest(inputStream : InputStream, outputStream : OutputStream,  context:Context) {
        val reader = BufferedReader(InputStreamReader(inputStream, Charset.forName("US-ASCII")))
        val queue = "https://sqs.ap-south-1.amazonaws.com/264057969062/ramuQueue"
        val sqsClient: SqsClient = SqsClient.builder()
            .region(Region.AP_SOUTH_1)
            .build()
        sendSingleMessage(sqsClient, queue,"call from api")
        sqsClient.close()
    }
    private fun sendSingleMessage(sqsClient: SqsClient, queue: String,data:String) {
        try {
            sqsClient.sendMessage(
                SendMessageRequest.builder()
                    .queueUrl(queue)
                    .messageBody(data)
                    .build()
            )
            println("Message has been sent successfully")
        } catch (e: SqsException) {
            System.err.println(e.awsErrorDetails().errorMessage())
            System.exit(1)
        }

    }
}

