import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubbredditSideBarComponent } from './subbreddit-side-bar.component';

describe('SubbredditSideBarComponent', () => {
  let component: SubbredditSideBarComponent;
  let fixture: ComponentFixture<SubbredditSideBarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubbredditSideBarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubbredditSideBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
