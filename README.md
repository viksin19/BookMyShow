# BookMyShow
Problem Statement: Design an admin portal for Book my show with frontend and backend support. Below 
is the requirement for the same.
Pages
1. Login page [A single admin user shall suffice]
2. Landing page
• Sidenav bar with two options:
1. Dashboard with movie stats [Any representation is fine](Default page)
2. Movies page
• All movies to be listed as a data table.
• Display below data points:
• Name
• Cast
• Language
• Genre
• Number of locations available for booking
• View details button against each row:
• On click of this button, it should show available shows in a particular location.
• Selecting a location is mandatory to show the available shows.
• Data points to show:
• Theatre name
• Timings
• Location
• Price
• Options to filter on
• Language
• Location at which a show is available
• Genre
• Options to sort on
• Language
• Name


#Front-end  - Angular 14, Angular Material, Chart.js, ng2-chart.js
Developed Single page application BookMyshow Admin portal for Admin.

1. Landing page - HomeComponent
2. SideNavBar + Tab+ router-outlet -- AppComponent
3. SignIn Dailog for Authenticating Admin.
4. Dashboard - DashBoardComponent [It contains Different charts component ]
5.MovieList - MovieListComponent
6.ViewDetails - MovieListComponent>Movie-List-dailog.Component

7. DashboardService and MovieService is for  providing services to each Component and backend api calls.


#Backend - Spring Boot, MySQL
run application at port: 8000
In application.properties change  ddl-auto from update to create.
Before that creatre a bookmyshow database schema in MySQL.

For All the table csv file is provide. import the data in respective table.


