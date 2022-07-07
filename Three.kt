import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

fun main() {
    print("Enter city name:")

    val city = readLine();

    val url = URL(
        "http://api.weatherapi.com/v1/current.json?" +
                "key=9a49d5b971fe42b38dc52140221506&q=" + city
    )

    val con: HttpURLConnection = url.openConnection() as HttpURLConnection
    con.setRequestMethod("GET")


    val temp = BufferedReader(
        InputStreamReader(con.inputStream)
    )
    var inputLine: String?
    val content = StringBuffer()
    while (temp.readLine().also { inputLine = it } != null) {
        content.append(inputLine)
    }
    temp.close()
    //println(content)

    val gson = Gson()
    val data = gson.fromJson(content.toString(), Getinfo::class.java);
    val loc = data.location.name
    val tempe = data.current.temp_c
    println("Your city:$loc")
    println("Temperature in $loc :$tempe")


}
data class Getinfo(
    var location: Location,
    var current: Current
)
data class Location(
    var name: String
)
data class Current(
    var temp_c: Float
)