import {async, ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';

import {TransationComponent} from './transation.component';
import {RouterTestingModule} from '@angular/router/testing';
import {FormsModule} from '@angular/forms';
import {BrowserModule, By} from '@angular/platform-browser';
import {DebugElement} from '@angular/core';
import {NgJsonEditorModule} from 'ang-jsoneditor';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {TransactionService} from '../../service/transaction.service';
import {of} from 'rxjs';

const mockUsers = [
  {result: 'Successfull', errorRecords: ''},
  {result: 'Juliette', errorRecords: ''}
];

describe('TransationComponent', () => {
  let component: TransationComponent;
  let fixture: ComponentFixture<TransationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        FormsModule,
        BrowserModule,
        NgJsonEditorModule,
        HttpClientTestingModule
      ],
      declarations: [TransationComponent],
      providers: [TransactionService]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransationComponent);
    component = fixture.componentInstance;
    fixture.autoDetectChanges();
  });

  it('Upload file should be called on verify button click', fakeAsync(() => {
    spyOn(component, 'sendData');
    fixture.detectChanges();
    const btn = fixture.debugElement.query(By.css('button'));
    btn.triggerEventHandler('click', null);
    tick(); // simulates the passage of time until all pending asynchronous activities finish
    fixture.detectChanges();
    expect(component.sendData).toHaveBeenCalled();

  }));

  it('On Successfull Save', fakeAsync(() => {
    const service: TransactionService = TestBed.get(TransactionService);
    const spy = spyOn(service, 'saveTransaction').and.returnValue(of(mockUsers));
    component.sendData();
    expect(service.saveTransaction).toHaveBeenCalled();
  }));

  it('On Error in Save', fakeAsync(() => {
    const service: TransactionService = TestBed.get(TransactionService);
    const spy = spyOn(service, 'saveTransaction').and.returnValue(null);
    component.sendData();
    expect(service.saveTransaction).toHaveBeenCalled();
  }));

  it('On Upload File check', fakeAsync(() => {
    const service: TransactionService = TestBed.get(TransactionService);
    const spy = spyOn(service, 'loadContentFromFile').and.returnValue(null);
    const eve: any = new Object();
    eve.target = new Object();
    eve.target.files = [];
    eve.target.files.push(new Object());
    component.onUploadFile(eve);
    expect(service.loadContentFromFile).toHaveBeenCalled();
  }));

  it('Should assign event to service', fakeAsync(() => {
    const service: TransactionService = TestBed.get(TransactionService);
    const eve: any = new Object();
    eve.target = new Object();
    eve.target.files = [];
    eve.target.files.push(new Object());
    component.changeLog(eve);
    expect(service.fileData.equals(eve));
  }));
});
