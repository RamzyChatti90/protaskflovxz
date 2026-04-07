import dayjs from 'dayjs/esm';
import { IProject } from 'app/entities/project/project.model';

export interface ITask {
  id: number;
  title?: string | null;
  description?: string | null;
  dueDate?: dayjs.Dayjs | null;
  completed?: boolean | null;
  priority?: number | null;
  project?: Pick<IProject, 'id'> | null;
}

export type NewTask = Omit<ITask, 'id'> & { id: null };
