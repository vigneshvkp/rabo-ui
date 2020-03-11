import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Transaction} from '../model/transaction.model';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {

  public fileData: any;

  constructor(private httpClient: HttpClient) {
  }

  saveTransaction(transactions: Transaction[]): Observable<any> {
    return this.httpClient.post(this.transactionPostUrl, transactions);
  }


  get transactionPostUrl() {
    return environment.transaction + '/statement';
  }

  loadContentFromFile(file): any {
    const fileReader = new FileReader();

    fileReader.readAsText(file);
    fileReader.onload = (e) => {
      try {
        this.fileData = JSON.parse(fileReader.result.toString());
        return this.fileData;
      } catch (e) {
        alert('Invalid Json File');
      }
    };
  }

}
