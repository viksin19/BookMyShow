import { Pipe, PipeTransform } from "@angular/core";

@Pipe({name: 'listToString'})
export class ListToStringPipe implements PipeTransform {
  transform(value: Array<string>): string {
    var resultString:string = '';
    console.log(value);
    value.forEach(val=>{
        resultString = resultString+val+'/ ';
    })
    console.log(resultString);
    return resultString;
  }
}