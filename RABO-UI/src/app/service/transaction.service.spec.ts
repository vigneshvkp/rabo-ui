import {inject, TestBed} from '@angular/core/testing';

import {TransactionService} from './transaction.service';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {HttpClient, HttpEvent, HttpEventType} from '@angular/common/http';

describe('TransactionService', () => {

  const mockUsers = [
    {result: 'Successfull', errorRecords: ''},
    {result: 'Juliette', errorRecords: ''}
  ];
  beforeEach(() => {
    const transactionServiceStub = jasmine.createSpyObj(TransactionService.name, {
      saveTransaction: () => mockUsers,
      getOrganizationIds: () => ['ORG1', 'ORG2'],
      getCurrentUserId: () => 'CURRENT_USER_ID'
    });

    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        RouterTestingModule,
      ],
      providers: []
    });

  });

  it('should be created', () => {
    const service: TransactionService = TestBed.get(TransactionService);
    expect(service).toBeTruthy();
  });
  it(
    'should get List of Errors on passing Transaction',
    inject(
      [HttpTestingController, TransactionService],
      (
        httpMock: HttpTestingController,
        dataService: TransactionService
      ) => {


        const transactions = [
          {
            reference: 123,
            accountNumber: 'A123',
            description: 'description',
            startBalance: 0,
            mutation: 1,
            endBalance: 1
          }
        ];
        dataService.saveTransaction(transactions).subscribe((event: HttpEvent<any>) => {
          switch (event.type) {
            case HttpEventType.Response:
              expect(event.body).toEqual(mockUsers);
          }
        });

        const mockReq = httpMock.expectOne(dataService.transactionPostUrl);

        expect(mockReq.cancelled).toBeFalsy();
        expect(mockReq.request.responseType).toEqual('json');

        mockReq.flush(mockUsers);

        httpMock.verify();
      }
    )
  );

  afterEach(inject([HttpTestingController], (httpMock: HttpTestingController) => {
    httpMock.verify();
  }));


});
