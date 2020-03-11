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


  reload = true;
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

  sendData() {
    this.transactionService.saveTransaction(this.transaction).subscribe(res => {
      this.result = res;
    }, error1 => {
      this.result.result = error1.error.error;
      this.result.message = error1.error.message;
      this.result.errorRecords = [];
    });
  }

  previewFile(e: any) {
    this.file = null;
    console.log('e- ', e);
    this.file = e.target.files[0];
    this.uploadDocument(this.file);
    e.target.value = '';
  }

  uploadDocument(file) {
    console.log('file - ', file);
    this.reload = false;
    this.reload = true;
    const fileReader = new FileReader();
    fileReader.onload = (e) => {
      try {
        this.transaction = JSON.parse(fileReader.result.toString());
      } catch (e) {
        alert('Invalid Json File');
      }
    };
    fileReader.readAsText(this.file);

  }

  changeLog(event = null) {
    if (event != null && event.type === 'change') {

    } else {
      this.transaction = event;
    }
  }

}
