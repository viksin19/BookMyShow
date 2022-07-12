export interface MovieList{
    movieId:any;
    movieName:string;
    movieRating?:number;
    movieLikes?:number;
    imgUrl?:string;
    movieYear:string;
    movieDuration:string;
    movieGenre?:Array<string>;
    movieLanguage?:Array<string>;
    movieCast?:Array<string>;
    movieLocation?:Array<string>;
    locationCount?:number;
}

export interface MovieTableData{
    movieName:string;
    movieGenre?:Array<string>;
    movieLanguage?:Array<string>;
    movieCast?:Array<string>;
    locationCount?:number;
}
export interface TheatreData{
    theatreId?:number;
    theatreName:string;
    theatreAddress:string;
    seatCount?:number;
    movieTimings?:Array<string>;
    moviePrice?:Array<Price>
}
export interface Price{
    seatType:string;
    seatPrice:number;
}