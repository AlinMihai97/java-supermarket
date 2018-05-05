package lists;

import departments.BookDepartment;
import departments.MusicDepartment;
import departments.SoftwareDepartment;
import departments.VideoDepartment;

public interface Visitor {

	public void visit(BookDepartment bookDepartment);
	public void visit(MusicDepartment musicDepartment);
	public void visit(SoftwareDepartment softwareDepartment);
	public void visit(VideoDepartment videoDepartment);

}
