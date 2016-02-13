# NYTSearch
Android app that allows you to search for NYT articles

Time spent: about 15 hours spent in total 

Completed user stories:

 * [x] Required: User can enter a search query that will display a grid of news articles using the thumbnail and headline from the New York Times Search API. (3 points)
 * [x] Required: User can click on "settings" which allows selection of advanced search options to filter results. (3 points)
 * [x] Required: User can configure advanced search filters such as: (points included above)
  * Begin Date (using a date picker)
  * News desk values (Arts, Fashion & Style, Sports)
  * Sort order (oldest or newest)
 * [x] Required: Subsequent searches will have any filters applied to the search results. (1 point)
 * [x] Required: User can tap on any article in results to view the contents in an embedded browser. (2 points)
 * [x] Required: User can scroll down "infinitely" to continue loading more news articles. The maximum number of articles is limited by the API search. (1 point)
 * [x] Optional: Replace Filter Settings Activity with a lightweight modal overlay. (2 points)

Challenges:

I spent some time creating a modal when simply extending Fragment before I realized I should extend DialogFragment to get that behavior of the box. 
Figuring out a UI that would be compact and still let the user select multiple News Desk values was challenging. I eventually came up with an autocomplete text field and displaying the selected values underneath.

Walkthrough of all user stories:

![Video Walkthrough](https://github.com/nidhik/NYTSearch/blob/master/codepath-assignment-week2-android.gif)
GIF created with [LiceCap](http://www.cockos.com/licecap/).
