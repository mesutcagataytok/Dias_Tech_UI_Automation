Feature: Hepsiburada Test
  As user I want to join Hepsiburada automation website

  @verifyOfPriceDetail
  Scenario: User should verify price

    Given I am on homepage
    Then I accept cookies
    Then I select tablet
    Then I select filter
    Then The highest priced product is selected
    Then I add to basket and verify