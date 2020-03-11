import {ErrorRecord} from './error-record.model';

export class Result {
  result: string;
  errorRecords: ErrorRecord[];
  message: string;

  constructor() {
    this.errorRecords = [];
  }
}
