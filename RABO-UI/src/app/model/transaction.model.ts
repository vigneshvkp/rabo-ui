export class Transaction {
  reference: number;
  accountNumber: string;
  description: string;
  startBalance: number;
  mutation: number;
  endBalance: number;

  constructor(ref?, an?, des?, sb?, mu?, eb?) {
    this.reference = ref;
    this.accountNumber = an;
    this.description = des;
    this.startBalance = sb;
    this.mutation = mu;
    this.endBalance = eb;
  }
}
