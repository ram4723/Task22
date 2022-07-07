import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.nio.charset.Charset

class ApiLambda : RequestStreamHandler {

    override fun handleRequest(inputStream: InputStream, outputStream: OutputStream, context: Context) {
        val reader = BufferedReader(InputStreamReader(inputStream, Charset.forName("US-ASCII")))
        val api = reader.toString()
        print("helloworld")
        println(api)
    }
}
/*
override  fun handleRequest(inputStream : InputStream, outputStream : OutputStream,  context:Context) {
      println("Request received from api")
    val reader = BufferedReader(InputStreamReader(inputStream, Charset.forName("US-ASCII")))
      val gson=Gson()
    val data = gson.fromJson(reader, HashMap::class.java)
      val queue = "https://sqs.ap-south-1.amazonaws.com/264057969062/AwsTaskQueue"      val sqsClient: SqsClient = SqsClient.builder()
          .region(Region.AP_SOUTH_1)
          .build()
      sendSingleMessage(sqsClient, queue,data.toString())
      sqsClient.close()

  }

  private fun sendSingleMessage(sqsClient: SqsClient, queue: String,data:String) {

      try {
          println("data recieved ${data}")
          sqsClient.sendMessage(
              SendMessageRequest.builder()
                  .queueUrl(queue)
                  .messageBody(data)
                  .delaySeconds(10)
                  .build()
          )
          println("Message has been sent successfully")
      } catch (e: SqsException) {
          System.err.println(e.awsErrorDetails().errorMessage())
          System.exit(1)
      }

  }
  fun main():Long {
        val num = 5
        var factorial: Long = 1
        for (i in 1..num) {
            factorial *= i.toLong()
        }
        println("Factorial of $num = $factorial")
        return factorial
    }
 */