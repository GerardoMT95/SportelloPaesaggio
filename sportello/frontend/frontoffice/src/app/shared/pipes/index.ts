import { FileSizePipe } from './filesize/filesize.pipe';
import { FilterPipe } from './filter.pipe';
import { SafeHtmlPipe } from './safehtml.pipe';


export const pipes = [FilterPipe,SafeHtmlPipe,FileSizePipe];

export { FilterPipe } from './filter.pipe';
export { SafeHtmlPipe } from './safehtml.pipe';
export { FileSizePipe } from './filesize/filesize.pipe';