
@tag
Feature: Buying an item
  Should decrease Score,
  and increase total income,
  income on item should be recalculated,
  click income should be recalculated,

  @tag1
  Scenario Outline: Buying an item should make appropiate changes to state,
    Given I want to buy item with index <index>
    And I can afford the item
    And I have <alreadyPurchased> items
    And the player has purchased <clickUpgrades> clickupgrades for the item
    When I buy <amount> of items
    Then The total income of the item should have increased
    And The score should have went down with the price for the item
    And statistics.totalIncomePrSecond should have increased with the added income
    And click income should have increased
    And itemsBought in statistics should go up with the purchased amount
    When I buy an income upgrade for the item
    Then the items income should have increased
    And the click income should have increased

    Examples: 
      | index | amount | clickUpgrades | alreadyPurchased |
      |     0 |      5 |             2 |               10 |
