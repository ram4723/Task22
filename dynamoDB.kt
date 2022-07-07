import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.Item
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome
import com.amazonaws.services.dynamodbv2.document.Table
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.SQSEvent
import com.google.gson.Gson
import software.amazon.awssdk.regions.Region
import java.util.*
class dynamoDB: RequestHandler<SQSEvent, Void?> {
    fun CreateItem()
    {
        val client: AmazonDynamoDB = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.AP_SOUTH_1).build()
        val dynamoDB = DynamoDB(client)
        val table: Table = dynamoDB.getTable("ramTable")
        val item: Item = Item()
            .withPrimaryKey("ID", "10")
            .withString("name", "raj")
            .withNumber("age",25)
            .withString("Country", "India")
        val outcome: PutItemOutcome = table.putItem(item)
        val item1: Item = Item()
            .withPrimaryKey("ID", "81")
            .withString("name", "rahul")
            .withNumber("age",24)
        val outcome1: PutItemOutcome = table.putItem(item1)
    }
    override fun handleRequest(event: SQSEvent, context: Context?): Void? {
        for (msg in event.records) {
            var message=msg.body.toString();
            print("message ${message}")
            CreateItem()
        }
        return null;
    }
}