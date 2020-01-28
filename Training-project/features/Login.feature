Feature: Test login field
@success
 Scenario Outline: Check login field for case sensitivity
   Given I am on main application page
   When I login as user with "<login>" and "<password>" and "<domain>"
   Then I see logout link
   Examples:
         | login  | password | domain |
         |zabavasa|pvtpvt2020|@list.ru|
         |ZABAVASA|pvtpvt2020|@list.ru|
         |Zabavasa|pvtpvt2020|@list.ru|
@fail
 Scenario Outline: valid login and invalid password
    Given I am on main application page
    When I login as user with "<login>" and INVALID "<password>" and "<domain>"
    Then I see error alert
    Examples:
          | login  | password  | domain |
          |zabavasa|aa         |@list.ru|
          |zabavasa|pvtpvt 2020|@list.ru|
