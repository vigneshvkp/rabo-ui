import {async, ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';

import {TransationComponent} from './transation.component';
import {RouterTestingModule} from '@angular/router/testing';
import {FormsModule} from '@angular/forms';
import {BrowserModule, By} from '@angular/platform-browser';
import {DebugElement} from '@angular/core';
import {NgJsonEditorModule} from 'ang-jsoneditor';
import {HttpClientTestingModule} from '@angular/common/http/testing';

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
      declarations: [TransationComponent]
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
});
