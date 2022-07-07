import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.Table
import com.amazonaws.services.dynamodbv2.xspec.S
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import com.google.gson.Gson
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.SendMessageRequest
import software.amazon.awssdk.services.sqs.model.SqsException
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.nio.charset.Charset

class ApiLambda : RequestStreamHandler {

    override  fun handleRequest(inputStream : InputStream, outputStream : OutputStream,  context:Context) {
        val reader = BufferedReader(InputStreamReader(inputStream, Charset.forName("US-ASCII")))
        val data = Gson().fromJson(reader, ResponseFromLambda::class.java)

        println("Received: $data")

        val incAuth = data.headers?.get("Authorization")
        val auth = getAuth()
        if(incAuth != auth) {
            val o = Out(
                statusCode = 401,
                body = "UnAuthorized User"
            )
            outputStream.write(Gson().toJson(o).toByteArray())
            return
        }

        val queue = "https://sqs.ap-south-1.amazonaws.com/264057969062/sai11"
        println("recieved data: $data")
        val sqsClient: SqsClient = SqsClient.builder()
            .region(Region.AP_SOUTH_1)
            .build()
        sendSingleMessage(sqsClient, queue,data.body)
        sqsClient.close()
        val o = Out(
            statusCode = 200,
            body = "Successful"
        )
        outputStream.write(Gson().toJson(o).toByteArray())
    }

    private fun getAuth(): String {
        val client: AmazonDynamoDB = AmazonDynamoDBClientBuilder.standard().build()
        val dynamoDB = DynamoDB(client)
        val table: Table = dynamoDB.getTable("sai11")
        val item = table.getItem("ID","xyz").toJSON()
        println("Item: $item")
        val ddbItem = Gson().fromJson(item, DDBPojo::class.java)
        println("DDB Item: $ddbItem")
        return ddbItem.authKey.toString()
    }

    private fun sendSingleMessage(sqsClient: SqsClient, queue: String, data:String) {
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

data class ResponseFromLambda(
    var body: String,
    var headers: Map<String, String>? = null
)

data class DDBPojo(
    var id: String? = null,
    var authKey: String? = null
)


data class Out(
    var statusCode: Int? = null,
    var body: String? = null
)