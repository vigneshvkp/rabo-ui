import {Component, OnInit} from '@angular/core';
import {Transaction} from '../../model/transaction.model';
import {TransactionService} from '../../service/transaction.service';
import {Result} from '../../model/result.model';
import {JsonEditorOptions} from 'ang-jsoneditor';

@Component({
  selector: 'app-transation',
  templateUrl: './transation.component.html',
  styleUrls: ['./transation.component.scss']
})
export class TransationComponent implements OnInit {


  transaction: any = [];
  result: Result;
  file: any;
  options = new JsonEditorOptions();
  options2 = new JsonEditorOptions();

  constructor(private transactionService: TransactionService) {
    this.transaction.push(new Transaction());
    this.options2.language = 'en';
    this.options.mode = 'code';
    this.options2.mode = 'text';
    this.options2.modes = this.options.modes = ['code', 'text', 'tree', 'view'];
    this.options2.statusBar = this.options.statusBar = false;
  }

  ngOnInit() {
    this.result = new Result();
  }

  /* make api call for getting transaction error detail*/
  sendData() {
    this.transactionService.saveTransaction(this.transaction).subscribe(res => {
      this.result = res;
    }, error1 => {
      this.result.result = error1.error.error;
      this.result.message = error1.error.message;
      this.result.errorRecords = [];
    });
  }

  /* Called from Upload file control*/
  uploadFile(e: any) {
    this.file = e.target.files[0];
    this.loadDocumentFromFile(this.file);
    e.target.value = '';
  }

  /* To read the file and load the content to the transaction variable */
  private loadDocumentFromFile(file) {
    const fileReader = new FileReader();
    fileReader.onload = (e) => {
      try {
        this.transaction = JSON.parse(fileReader.result.toString());
      } catch (e) {
        alert('Invalid Json File');
      }
    };
    fileReader.readAsText(file);

  }

  changeLog(event = null) {
    if (event != null && event.type === 'change') {
    console.log('Not impl');
    } else {
      this.transaction = event;
    }
  }

}
