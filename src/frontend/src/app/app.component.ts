import {Component, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

@Injectable()
export class AppComponent {
  fileToUpload: File = null;
  errorMessage: null;
  isSuccess: boolean = false;
  response: string = null;

  constructor(private http: HttpClient) {
  }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }


  /*onSubmit() {
    const url = 'http://127.0.0.1:8080/hello';
    this.http.get(url)
      .subscribe((data: string) => {
        this.response = data;
      }, console.log)
  }*/

  onSubmit() {
    this.errorMessage = null;
    this.isSuccess = false;

    const url = 'http://127.0.0.1:8080/load';
    const formData: FormData = new FormData();
    formData.append('fileKey', this.fileToUpload, this.fileToUpload.name);
    this.http
      .post(url, formData)
      .subscribe(data => {
        console.log(data);
        this.isSuccess = true;
      }, error => {
        console.log(error);
        this.errorMessage = error.message || '';
      });
  }
}
