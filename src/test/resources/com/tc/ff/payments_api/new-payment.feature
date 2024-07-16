#Feature: Create a new payment
#
#  Scenario: Successfully creating a new payment
#    Given there is a valid "RegisterPendingPaymentRequest"
#    When I send a POST request to "/payments" with the request body
#    Then the response should have a status of 201
#    And the response body should match the expected "PaymentResponse"
