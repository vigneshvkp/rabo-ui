import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ResultdataComponent } from './resultdata.component';

describe('ResultdataComponent', () => {
  let component: ResultdataComponent;
  let fixture: ComponentFixture<ResultdataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ResultdataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResultdataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
