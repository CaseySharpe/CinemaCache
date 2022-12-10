# CinemaCache

ACCESSIBILITY
- CinemaCache image for login and register screens (the big one under the app header) has a low contrast ratio and comes up in Android Studios accessibility checker. 
Consider deleting. 

TODO
- Data is poorly managed
  - movies can probably be read from a json but we need to add more
  - user and review data should be stored and accessed differently (because right now the assets are read only)
  - data duplication throughout program
- Activities are not closed properly, figure out issues with the way activities are called
- Add snackbars
  - When review is created
  - When movie is added to watchlist
- Add form that can write to file (goes with data management)
- Make movie poster clickable to go to details page
- Make details page back button navigate to last page
- log-in functionality
  - Ananya - not sure how to change the title in the top menu bar for the login and register views
  



  
