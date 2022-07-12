import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { HomeComponent } from './home/home.component';
import { MovieListComponent } from './movie-list/movie-list.component';

const routes: Routes = [{
  path:'',component:HomeComponent
},
{
path:'bookmyshow',component:HomeComponent
},
{
  path:'dashboard',component:DashboardComponent

},
{
  path:'movielist',component:MovieListComponent
}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
