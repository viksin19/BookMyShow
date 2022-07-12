import { Component, Inject, OnInit } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { MovieList, TheatreData } from "src/app/modal-interfaces/movieList.model";
import { MovieService } from "src/app/MovieServices/movie-service.service";

@Component({
    selector: 'movie-detail-dailogue',
    templateUrl: 'movie-details.component.html',
    styleUrls:['../movie-list.component.css']
  })
  export class MovieDetailsDailog implements OnInit{
    locationSelected:string = 'Select Location';
    availableLocation:Array<any> = [];
    isLocationSelected:boolean = false;
    theatreList:TheatreData[] = []; 
    constructor(@Inject(MAT_DIALOG_DATA) public movieData: MovieList,private movieService:MovieService,
    public dialogRef: MatDialogRef<MovieDetailsDailog>) {}
    ngOnInit(): void {
        console.log(this.movieData);
        if(this.movieData){
          this.availableLocation = this.movieService.getCityUrlMapData(this.movieData.movieLocation);
        }
        this.dialogRef.updateSize('800px','540px');
    }

    enableLocationSelection(){
        this.isLocationSelected = false;
    }

    onSelectLocation(location:any){
        this.isLocationSelected = true;
        console.log(location);
        this.theatreList = [];
        this.movieService.getMovieTheatre('viewDetails',this.movieData.movieId,location.locationId).subscribe(theatreData=>{
           if(theatreData?.locationId==location.locationId) 
           this.theatreList = theatreData?.theatreList;
        });
        this.locationSelected = location?.city;
    }

  }