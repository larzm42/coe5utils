package coe5utils;
/* This file is part of coe5utils.
 *
 * coe5utils is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * dom5utils is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with coe5utils.  If not, see <http://www.gnu.org/licenses/>.
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class MonsterStatIndexer extends AbstractStatIndexer {

	public static void main(String[] args) {
		run();
	}
	
	public static void run() {
		FileInputStream stream = null;
        List<Monster> monsterList = new ArrayList<Monster>();

		try {
			long startIndex = Starts.MONSTER;
	        int ch;
			stream = new FileInputStream(EXE_NAME);			
			stream.skip(startIndex);
			
			// Name
			InputStreamReader isr = new InputStreamReader(stream, "ISO-8859-1");
	        Reader in = new BufferedReader(isr);
	        int rowNumber = 0;
			while ((ch = in.read()) > -1) {
				StringBuffer name = new StringBuffer();
				while (ch != 0) {
					name.append((char)ch);
					ch = in.read();
				}
				if (name.length() == 0) {
					continue;
				}
				if (name.toString().equals("end")) {
					break;
				}
				in.close();
				
				Monster monster = new Monster();
				monster.setId(rowNumber);
				monster.setName(name.toString());
				monster.setArmor(getBytes2(startIndex + 52));
				monster.setHp(getBytes2(startIndex + 54));
				monster.setStr(getBytes2(startIndex + 56));
				monster.setMor(getBytes2(startIndex + 58));
				monster.setRank(getBytes2(startIndex + 60));
				monster.setMr(getBytes2(startIndex + 448));
				monsterList.add(monster);

				stream = new FileInputStream(EXE_NAME);		
				startIndex = startIndex + Starts.MONSTER_SIZE;
				stream.skip(startIndex);
				isr = new InputStreamReader(stream, "ISO-8859-1");
		        in = new BufferedReader(isr);

				rowNumber++;
			}
			in.close();
			stream.close();
			
			
			for (Monster monster : monsterList) {
				System.out.println(monster.getId() + " " + monster.getName());
				System.out.println("Armor: " + monster.getArmor() +
						           " HP: " + monster.getHp() +
						           " Str: " + monster.getStr() + 
						           " Mor: " + monster.getMor() + 
						           " MR: " + monster.getMr() + 
						           " Rank: " + monster.getRank());
			}

			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
