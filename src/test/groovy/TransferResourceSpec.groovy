import jakarta.inject.Inject
import org.example.dao.AccountDao

import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class TransferResourceSpec extends BaseSpecification implements AccountTestData{

    private static final TRANSFER_PATH = "/transfer"
    private static final TRANSFER_URL =  HOST + TRANSFER_PATH

    private HttpClient httpClient = HttpClient.newHttpClient()

    def "should return ok when transfer money"() {
        given:
        def request = HttpRequest.newBuilder()
        .uri(new URI(TRANSFER_URL))
        .POST(HttpRequest.BodyPublishers.ofString(String.format("""
        {
            "accountFrom": "%s",
            "accountTo": "%s",
            "amount": 100
        }
        """, "902cc3fe-bcd2-4072-a069-d3688235f8bc", "dd5e091d-fc50-4d39-a958-8892873a6829"))).build()

        when:
        def response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        then:
        """{\n    "status": "OK",\n    "errorMessage":""\n}""" == response.body()
    }

    def "should return exception message when accountFrom not specified"() {
        given:
        def request = HttpRequest.newBuilder()
                .uri(new URI(TRANSFER_URL))
                .POST(HttpRequest.BodyPublishers.ofString(String.format("""
        {
            "accountTo": "%s",
            "amount": 10
        }
        """, ACCOUNT_2.toString()))).build()

        when:
        def response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        then:
        response.body().contains("accountFrom must not be null")
    }
}
