package fritz.test.recipe.model;

public enum Difficulty {
	EASY {
		@Override
		public String toString() {
			return "Easy";
		}
	},
	MEDIUM {
		@Override
		public String toString() {
			return "Medium";
		}
	}, HARD {
		@Override
		public String toString() {
			return "Hard";
		}
	}, MODERATE {
		@Override
		public String toString() {
			return "Moderate";
		}
	}
}