import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

fun main(){
    println("Enter Country:")
    val country = readLine()
    val url = URL(
        "https://vaccovid-coronavirus-vaccine-and-treatment-tracker.p.rapidapi.com/api/npm-covid-data/country-report-iso-based/India/Ind"
    )

    val con: HttpURLConnection = url.openConnection() as HttpURLConnection
//    con.setRequestProperty("Content-Type", "application/json")
    con.setRequestProperty("X-RapidAPI-Key", "1f245d1074msh09391355bc59293p1ab271jsn587aa1bc0ea8")
    con.setRequestProperty("X-RapidAPI-Host", "vaccovid-coronavirus-vaccine-and-treatment-tracker.p.rapidapi.com")
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
    print(content)

}