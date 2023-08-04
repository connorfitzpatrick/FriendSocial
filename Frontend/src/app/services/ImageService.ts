import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ImageService {
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

    console.log('Hello from bottom of ImageService');

    return this.http.post<any>(`${apiUrl}`, formData, httpOptions);
  }
}
