import dayjs from 'dayjs/esm';

import { IProject, NewProject } from './project.model';

export const sampleWithRequiredData: IProject = {
  id: 22823,
  name: 'anxiously',
};

export const sampleWithPartialData: IProject = {
  id: 21731,
  name: 'maul busily',
  startDate: dayjs('2026-04-07'),
};

export const sampleWithFullData: IProject = {
  id: 1375,
  name: 'tray pack hydrolyze',
  description: '../fake-data/blob/hipster.txt',
  startDate: dayjs('2026-04-06'),
  endDate: dayjs('2026-04-06'),
  completed: true,
};

export const sampleWithNewData: NewProject = {
  name: 'zowie vice',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
