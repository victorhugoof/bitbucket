import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export abstract class BaseService<M> {

  constructor (protected http: HttpClient) { }
  url = '';

  consultarCodigo(id: number): Observable<M> {
    return this.get(this.url + '/' + id);
  }

  consultarTodos(): Observable<M[]> {
    return this.get(this.url + '/consultarTodos');
  }

  salvar(model: any): Observable<M> {
    return this.post(this.url, model);
  }

  atualizar(model: any): Observable<M> {
    return this.put(this.url, model);
  }

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
      //this.storageService.logout();
    }

    let errMsg: any;
    if (!(error instanceof HttpErrorResponse)) {
      errMsg = error.message ? error.message : error.toString();
    } else {

      if (error.status === 0) {
        errMsg = 'Não foi possível conectar-se ao servidor. Verifique sua conexão com a internet.';
      } else {
        errMsg = error.error;
      }
    }
    return throwError(errMsg);
  }

  private buildHeaders(headers: HttpHeaders): HttpHeaders {

    if (!headers) {
      headers = new HttpHeaders();
    }
    //if (this.storageService.oauthModel && this.storageService.oauthModel.access_token) {
     //headers = headers.append('Authorization', 'Bearer ' + this.storageService.oauthModel.access_token);
    //}

    headers = headers.append('Authorization', 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NjU5MDE5ODYsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9ST0xFIl0sImp0aSI6IjNjNmMyZmUzLTI1NDktNDBkZi05MDliLTBiNDQ4NTFjZWJkMCIsImNsaWVudF9pZCI6ImFuZ3VsYXIiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXX0.mTGplDvGCpHNI3aWrbi-8X1lrLb88Jrgsxh2hpt5sik');
    return headers;
  }
}
