import {Component, OnChanges, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import {Transaction} from '../../model/transaction.model';
import {TransactionService} from '../../service/transaction.service';
import {Result} from '../../model/result.model';
import {JsonEditorComponent, JsonEditorOptions} from 'ang-jsoneditor';


@Component({
  selector: 'app-transation',
  templateUrl: './transation.component.html',
  styleUrls: ['./transation.component.scss']
})
export class TransationComponent implements OnInit {

  @ViewChild(JsonEditorComponent, {static: true}) editor: JsonEditorComponent;

  isValidJson = true;
  file: any;
  result: Result;
  options = new JsonEditorOptions();
  options2 = new JsonEditorOptions();

  constructor(private transactionService: TransactionService) {
    this.transactionService.fileData = [];
    this.transactionService.fileData.push(new Transaction());
    this.options2.language = 'en';
    this.options.mode = 'code';
    this.options2.mode = 'text';
    this.options2.modes = this.options.modes = ['code', 'text', 'tree', 'view'];
    this.options2.statusBar = this.options.statusBar = true;
  }


  ngOnInit() {
    this.result = new Result();
  }

  sendData() {
    this.transactionService.saveTransaction(this.transactionService.fileData).subscribe(res => {
      this.result = res;
    }, error1 => {
      this.result.result = error1.error.error;
      this.result.message = error1.error.message;
      this.result.errorRecords = [];
    });
  }

  onUploadFile(e: any): any {
    this.file = null;
    this.file = e.target.files[0];
    e.target.value = '';
    this.transactionService.loadContentFromFile(this.file);
  }


  changeLog(event = null) {
    console.log('eee -', event);
    if (event != null && event.type === 'change') {
      // it is an un wanted event so ignoring it.
    } else {
      this.transactionService.fileData = event;
    }
  }

}
