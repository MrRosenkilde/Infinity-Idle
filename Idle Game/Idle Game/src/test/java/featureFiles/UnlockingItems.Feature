@tag
Feature: UnlockingNewItems
  I want to use this template to test that a new item is always unlocked
  when the amount of items with the highest index exceeds then

  @tag1
  Scenario Outline: Getting 10 of an item unlocks the next itme
    Given I have currently unlocked <unlockedItems> items
    And i have bought 9 items
    And I can afford another item
    When I buy 1 more
    Then A new item should have been added to state

  @tag2
  Scenario Outline: Getting more then 10 of an item unlocks the next itme
    Given I have currently unlocked <unlockedItems> amount of items
    And i have bought 9 items
    And I can afford another item
    When I buy 2 more
    Then A new item should have been added to state

    Examples: 
      | unlockedItems |
      |             1 |
      |           999 |
