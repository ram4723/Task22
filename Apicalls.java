fun main(){
        OkHttpClient client = new OkHttpClient().newBuilder()
        .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
        .url("https://vaccovid-coronavirus-vaccine-and-treatment-tracker.p.rapidapi.com/api/npm-covid-data/country-report-iso-based/India/Ind")
        .method("GET", body)
        .addHeader("X-RapidAPI-Key", "1f245d1074msh09391355bc59293p1ab271jsn587aa1bc0ea8")
        .addHeader("X-RapidAPI-Host", "vaccovid-coronavirus-vaccine-and-treatment-tracker.p.rapidapi.com")
        .build();
        Response response = client.newCall(request).execute();
}
