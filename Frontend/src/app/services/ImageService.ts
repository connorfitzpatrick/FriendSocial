import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ImageService {
  private profilePicSubject: BehaviorSubject<string | null> =
    new BehaviorSubject<string | null>(null);

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

  async getImage(fileName: string): Promise<string> {
    const token = localStorage.getItem('token');
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      }),
      responseType: 'blob' as 'json', // Set the responseType to 'blob'
    };

    try {
      const response = await this.http
        .get(`http://localhost:8080/api/v1/image/${fileName}`, httpOptions)
        .toPromise();

      // Convert the Blob data to a URL that can be used as the image source.
      const imageUrl = URL.createObjectURL(response as Blob);
      return imageUrl; // Return the imageUrl directly as a string
    } catch (error) {
      console.error('Error getting image:', error);
      throw error; // Rethrow the error if needed
    }
  }

  setProfilePicUrl(url: string | null): void {
    this.profilePicSubject.next(url);
  }

  getProfilePicUrl(): string | null {
    return this.profilePicSubject.getValue();
  }
}
