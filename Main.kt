import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.nio.charset.Charset

class Main : RequestStreamHandler {

    override fun handleRequest(inputStream: InputStream, outputStream: OutputStream, context: Context) {
        val reader = BufferedReader(InputStreamReader(inputStream, Charset.forName("US-ASCII")))
        for (line in reader.lines()) {
            println(line)
        }
        print("helloworld")
    }
}