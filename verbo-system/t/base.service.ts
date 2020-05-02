import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpErrorResponse } from '@angular/common/http';

import { Observable, of, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { environment } from '../../environments/environment';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export abstract class BaseService {

  constructor (protected http: HttpClient, protected storageService: StorageService) { }

  get<T>(url: string, params?: HttpParams, headers?: HttpHeaders): Observable<T> {

    headers = this.buildHeaders(headers);
    return this.http.get<T>(environment.urlApi + url, { headers: headers, params: params }).pipe(
      catchError(e => this.handleError(e))
    );
  }

  post<T>(url: string, body?: any, params?: HttpParams, headers?: HttpHeaders): Observable<T> {

    headers = this.buildHeaders(headers);
    return this.http.post<T>(environment.urlApi + url, body, { headers: headers, params: params }).pipe(
      catchError(e => this.handleError(e))
    );
  }

  put<T>(url: string, body?: any, params?: HttpParams, headers?: HttpHeaders): Observable<T> {

    headers = this.buildHeaders(headers);
    return this.http.put<T>(environment.urlApi + url, body, { headers: headers, params: params }).pipe(
      catchError(e => this.handleError(e))
    );
  }

  delete<T>(url: string, params?: HttpParams, headers?: HttpHeaders): Observable<T> {

    headers = this.buildHeaders(headers);
    return this.http.delete<T>(environment.urlApi + url, { headers: headers, params: params }).pipe(
      catchError(e => this.handleError(e))
    );
  }

  postNoHeaders<T>(url: string, body?: any, params?: HttpParams, headers?: HttpHeaders): Observable<T> {

    return this.http.post<T>(environment.urlApi + url, body, { headers: headers, params: params }).pipe(
      catchError(e => this.handleError(e))
    );
  }

  protected handleError(error: any) {

    console.error(error);
    if (error && error.status === 401) {
      this.storageService.logout();
    }

    let errMsg: string;
    if (!(error instanceof HttpErrorResponse)) {
      errMsg = error.message ? error.message : error.toString();
    } else {

      if (error.status === 0) {
        errMsg = 'Não foi possível conectar-se ao servidor. Verifique sua conexão com a internet.';
      } else if (error.error.message) {
        errMsg = error.error.message;
      } else if (error.error.error_description) {
        errMsg = error.error.error_description;
      } else {
        errMsg = error.error.toString();
      }
    }
    return throwError(errMsg);
  }

  private buildHeaders(headers: HttpHeaders): HttpHeaders {

    if (!headers) {
      headers = new HttpHeaders();
    }
    if (this.storageService.oauthModel && this.storageService.oauthModel.access_token) {
      headers = headers.append('Authorization', 'Bearer ' + this.storageService.oauthModel.access_token);
    }
    return headers;
  }
}
