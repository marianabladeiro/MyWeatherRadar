package weatherRadar;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import weatherRadar.IpmaCityForecast;
import weatherRadar.IpmaService;

import java.util.logging.Logger;

/**
 * demonstrates the use of the IPMA API for weather forecast
 */
public class App {

    private static final int CITY_ID_AVEIRO = 1010500;
    private static final Map<String, Integer> CITIES = new HashMap<String, Integer>() { 
        {
            put("aveiro", 10105500);
            put("beja", 1020500);
            put("braga", 1030300);
            put("bragança", 1040200);
            put("castelo branco", 1050200);
            put("coimbra", 1060300);
            put("évora", 1070500);
            put("faro", 1080500);
            put("guarda", 1090700);
            put("leiria", 1100900);
            put("lisboa", 1110600);
            put("portalegre", 1121400);
            put("porto", 1131200);
            put("santarém", 1141600);
            put("setúbal", 1151200);
            put("viana do castelo", 1160900);
            put("vila real", 1171400);
            put("viseu", 1182300);
            put("funchal", 2310300);
            put("porto santo", 2320100);
            put("vila do porto", 3410100);
            put("ponta delgada", 3430100);
            put("angra do heroísmo", 2320100);
            put("santa cruza da graciosa", 3440100);
            put("velas", 3450200);
            put("madalena", 3460200);
            put("horta", 3470100);
            put("santa cruz das flores", 3480200);
            put("vila do corvo", 3490100);

        }
    };

    /*
    loggers provide a better alternative to System.out.println
    https://rules.sonarsource.com/java/tag/bad-practice/RSPEC-106
     */
    private static final Logger logger = Logger.getLogger(App.class.getName());

    public static void  main(String[] args ) {

        /*
        get a retrofit instance, loaded with the GSon lib to convert JSON into objects
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.ipma.pt/open-data/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IpmaService service = retrofit.create(IpmaService.class);
        Call<IpmaCityForecast> callSync = service.getForecastForACity(CITIES.get(args[0].toLowerCase()));

        try {
            Response<IpmaCityForecast> apiResponse = callSync.execute();
            IpmaCityForecast forecast = apiResponse.body();

            if (forecast != null) {
                logger.info( "max temp for today: " + forecast.getData().
                        listIterator().next().getTMax());
            } else {
                logger.info( "No results!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}