import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CrediarioComponent } from './crediario.component';

describe('CrediarioComponent', () => {
  let component: CrediarioComponent;
  let fixture: ComponentFixture<CrediarioComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CrediarioComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CrediarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
