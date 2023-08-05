import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class ImageService {
  profilePictureUrl = '';

  constructor(private http: HttpClient) {}

  uploadImage(selectedImage: File): Observable<any> {
    const apiUrl = `http://localhost:8080/api/v1/upload`;
    const token = localStorage.getItem('token');

    const httpOptions = {
      headers: new HttpHeaders({
        Authorization: `Bearer ${token}`,
      }),
      'Content-Type': undefined,
    };

    const formData = new FormData();
    formData.append('file', selectedImage);

    return this.http.post<any>(`${apiUrl}`, formData, httpOptions);
  }

  // getImage(fileName: any): string {
  //   // Replace 'fileName.jpg' with the actual name of the profile picture file.
  //   // You can get this information from the user data or any other source.
  //   console.log('Hello from getImage()');
  //   console.log(fileName);

  //   const token = localStorage.getItem('token');

  //   const httpOptions = {
  //     headers: new HttpHeaders({
  //       Authorization: `Bearer ${token}`,
  //     }),
  //     responseType: 'blob',
  //   };

  //   this.http
  //     .get(`http://localhost:8080/api/v1/image/'${fileName}`, httpOptions)
  //     .subscribe((response: Blob) => {
  //       // Convert the Blob data to a URL that can be used as the image source.
  //       const imageUrl = URL.createObjectURL(response);
  //       this.profilePictureUrl = imageUrl;
  //     });
  //   console.log(this.profilePictureUrl);
  //   return this.profilePictureUrl;
  // }

  getImage(fileName: string): Observable<string> {
    const token = localStorage.getItem('token');

    const httpOptions = {
      headers: new HttpHeaders({
        Authorization: `Bearer ${token}`,
      }),
      responseType: 'blob' as 'json', // Specify the responseType as 'blob' as 'json'
    };

    return this.http
      .get(`http://localhost:8080/api/v1/image/${fileName}`, httpOptions)
      .pipe(
        map((response: any) => response as Blob), // Type assertion to Blob
        map((blobResponse: Blob) => {
          // Convert the Blob data to a URL that can be used as the image source.
          const imageUrl = URL.createObjectURL(blobResponse);
          return imageUrl;
        })
      );
  }
}
