import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request


fun main() {
    println("Enter a Country:")
    val country = readLine()
    println("Enter a CountryCode:")
    val countryCode = readLine()
    val client = OkHttpClient().newBuilder().build()
    val request = Request.Builder()
        .url("https://vaccovid-coronavirus-vaccine-and-treatment-tracker.p.rapidapi.com/api/npm-covid-data/country-report-iso-based/$country/$countryCode")
        .get()
        .addHeader("X-RapidAPI-Key", "1f245d1074msh09391355bc59293p1ab271jsn587aa1bc0ea8")
        .addHeader("X-RapidAPI-Host", "vaccovid-coronavirus-vaccine-and-treatment-tracker.p.rapidapi.com")
        .build()
    val response = client.newCall(request).execute()
    //println(response.body?.string())


    val gson = Gson()
    val listType = object : TypeToken<ArrayList<Info>>() {}.type
    val data: ArrayList<Info> = gson.fromJson(response.body?.string(), listType);
    var case = data.get(0).TotalCases.toInt()
    var cases = data.get(0).Infection_Risk
    println("Total cases in $country: $case")
    println("Infection Ratio in $country:$cases")
   val client1 = OkHttpClient()
    val request1 = Request.Builder()
        .url("https://world-population.p.rapidapi.com/population?country_name=$country")
        .addHeader("X-RapidAPI-Key", "1f245d1074msh09391355bc59293p1ab271jsn587aa1bc0ea8")
        .addHeader("X-RapidAPI-Host", "world-population.p.rapidapi.com")
        .get()
        .build()
    val response1 = client1.newCall(request1).execute()
//   println(response1.body?.string())
   val data1 = gson.fromJson(response1.body?.string(), Info1::class.java);
    var popul = data1.body.population.toInt()
   println("Population in $country:$popul")
    //val infe = (case / popul.toFloat() * 100)
    //println("Infection ratio in $country:$infe")
}
data class Info(
 var TotalCases:String,
 var Infection_Risk:String

)
data class Info1(
    var body : Popu
)
data class Popu(
    var population:String
)
//"Infection_Risk":3.08