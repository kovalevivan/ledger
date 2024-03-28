import static org.example.dao.AccountBalanceTestData.ACCOUNT_1
import static org.example.dao.AccountBalanceTestData.ACCOUNT_2
import spock.lang.Specification

import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class TransferResourceSpec extends BaseSpecification{

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
            "amount": 1
        }
        """, ACCOUNT_1, ACCOUNT_2))).build()

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
            "amount": 1
        }
        """, ACCOUNT_2))).build()

        when:
        def response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        then:
        response.body().contains("accountFrom must not be null")
    }
}
