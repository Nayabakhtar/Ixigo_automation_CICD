Feature: Ixigo Flight Booking

  Scenario: Book a round trip with specific cities and passengers
  
  #User launch the Ixigo homepage
  
    Given User on the Ixigo homepage
		And User select the "Round Trip" tab
		
	#User enter travel details
    When User enter From as "Kolkata" and To as "New Delhi, Delhi, India"
		And User select departure date "26" June, 2025 
    And User select return date "3" "August 2025"
    And User select 3 Adults, 1 Children, and 1 Infants
    
	#User click the Search button
    And User click search
    And User switch to the new browser tab
   
  #User apply filter options on search results
    When User clicks and selects the 1 stop checkbox in Filter
    And User click on the Earliest Departure radio button
    
  #User click on the “Book” button
    When User clicks on the Book button
    Then the booking action should be triggered
    
  #User validate the total fare amounts
    When User captures both total amounts
    Then both total amounts should match
    
  #User take a screenshot of the final page
    And User take a screenshot of the Review Flight Details page
