import jakarta.inject.Inject
import org.example.dao.AccountDao

import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class TransferResourceSpec extends BaseSpecification implements AccountTestData{

    private static final TRANSFER_PATH = "/transfer"
    private static final TRANSFER_URL =  HOST + TRANSFER_PATH

    private HttpClient httpClient = HttpClient.newHttpClient()

    def "should return exception message when accounts not existed"() {
        given:
        def request = HttpRequest.newBuilder()
        .uri(new URI(TRANSFER_URL))
        .POST(HttpRequest.BodyPublishers.ofString(String.format("""
        {
            "accountFrom": "%s",
            "accountTo": "%s",
            "amount": 100
        }
        """, ACCOUNT_1.toString(), ACCOUNT_2.toString()))).build()

        when:
        def response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        then:
        response.body().contains(String.format("Account %s doesn't exist", ACCOUNT_1.toString()))
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
