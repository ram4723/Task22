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


    data class Data(

        val ID:String,
        val name:String,
        val message:String

    )

    class Second: RequestHandler<SQSEvent, Void?> {


        fun CreateItem(data:Data)
        {
            val client: AmazonDynamoDB = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.AP_SOUTH_1).build()
            val dynamoDB = DynamoDB(client)
            //Get the Table
            val table: Table = dynamoDB.getTable("ramTable")

            // Build the item
            val item: Item = Item()
                .withPrimaryKey("ID", UUID.randomUUID().toString())
                .withString("name", data.name)
                .withString("message",data.message)

            val outcome: PutItemOutcome = table.putItem(item)

        }

        override fun handleRequest(event: SQSEvent, context: Context?): Void? {
            for (msg in event.records) {
                var message=msg.body.toString();

                val gson= Gson()
                val data= gson.fromJson(message, Data::class.java)
                println("recieved : $data")
                CreateItem(data)
            }

            return null;
        }
    }
